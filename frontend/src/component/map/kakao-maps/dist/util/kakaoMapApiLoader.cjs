'use strict';

var constants = require('./constants.cjs');

let LoaderStatus = /*#__PURE__*/function (LoaderStatus) {
  LoaderStatus[LoaderStatus["INITIALIZED"] = 0] = "INITIALIZED";
  LoaderStatus[LoaderStatus["LOADING"] = 1] = "LOADING";
  LoaderStatus[LoaderStatus["SUCCESS"] = 2] = "SUCCESS";
  LoaderStatus[LoaderStatus["FAILURE"] = 3] = "FAILURE";
  return LoaderStatus;
}({});
const DEFAULT_ID = `${constants.SIGNATURE}_Loader`;
/**
 * Kakao Map Api Loader
 *
 * `new Loader(options).load()` 함수를 이용하여 Api를 비동기적으로 삽입할 수 있습니다.
 *
 * 해당 Loader를 이용시 `react-kakao-maps-sdk` 내부에서 injection 되는 이벤트를 감지하여 kakao map api 로딩 이후에 렌더링을 진행합니다.
 */
class Loader {
  static loadEventCallback = new Set();
  callbacks = [];
  done = false;
  loading = false;
  errors = [];
  constructor(_ref) {
    let {
      appkey,
      id = DEFAULT_ID,
      libraries = [],
      nonce,
      retries = 3,
      url = "//dapi.kakao.com/v2/maps/sdk.js"
    } = _ref;
    this.id = id;
    this.appkey = appkey;
    this.libraries = libraries;
    this.nonce = nonce;
    this.retries = retries;
    this.url = url;
    if (Loader.instance) {
      if (Loader.instance.status !== LoaderStatus.FAILURE && !Loader.equalOptions(this.options, Loader.instance.options)) {
        throw new Error(`Loader must not be called again with different options. \n${JSON.stringify(this.options, null, 2)}\n!==\n${JSON.stringify(Loader.instance.options, null, 2)}`);
      }
      Loader.instance.reset();
    }
    Loader.instance = this;
  }
  get options() {
    return {
      appkey: this.appkey,
      id: this.id,
      libraries: this.libraries,
      nonce: this.nonce,
      retries: this.retries,
      url: this.url
    };
  }
  static addLoadEventLisnter(callback) {
    if (window.kakao && window.kakao.maps) {
      window.kakao.maps.load(callback);
    }
    Loader.loadEventCallback.add(callback);
    return callback;
  }
  static removeLoadEventLisnter(callback) {
    return Loader.loadEventCallback.delete(callback);
  }
  load() {
    return new Promise((resolve, reject) => {
      this.loadCallback(err => {
        if (!err) {
          resolve(window.kakao);
        } else {
          reject(err);
        }
      });
    });
  }
  get status() {
    if (this.onEvent) {
      return LoaderStatus.FAILURE;
    }
    if (this.done) {
      return LoaderStatus.SUCCESS;
    }
    if (this.loading) {
      return LoaderStatus.LOADING;
    }
    return LoaderStatus.INITIALIZED;
  }
  get failed() {
    return this.done && !this.loading && this.errors.length >= this.retries + 1;
  }
  loadCallback(fn) {
    this.callbacks.push(fn);
    this.execute();
  }
  resetIfRetryingFailed() {
    if (this.failed) {
      this.reset();
    }
  }
  reset() {
    this.deleteScript();
    this.done = true;
    this.loading = false;
    this.errors = [];
    this.onEvent = undefined;
  }
  execute() {
    this.resetIfRetryingFailed();
    if (this.done) {
      this.callback();
    } else {
      if (window.kakao && window.kakao.maps) {
        console.warn("Kakao Maps이 이미 외부 요소에 의해 로딩되어 있습니다." + "설정한 옵션과 일치 하지 않을 수 있으며, 이에 따른 예상치 동작이 발생할 수 있습니다.");
        window.kakao.maps.load(this.callback);
        return;
      }
      if (!this.loading) {
        this.loading = true;
        this.setScript();
      }
    }
  }
  setScript() {
    if (document.getElementById(this.id)) {
      this.callback();
    }
    const url = this.createUrl();
    const script = document.createElement("script");
    script.id = this.id;
    script.type = "text/javascript";
    script.src = url;
    script.onerror = this.loadErrorCallback.bind(this);
    script.onload = this.callback.bind(this);
    script.defer = true;
    script.async = true;
    if (this.nonce) {
      script.nonce = this.nonce;
    }
    document.head.appendChild(script);
  }
  loadErrorCallback(event) {
    this.errors.push(event);
    if (this.errors.length <= this.retries) {
      const delay = this.errors.length * 2 ** this.errors.length;
      console.log(`Failed to load Kakao Maps script, retrying in ${delay} ms.`);
      setTimeout(() => {
        this.deleteScript();
        this.setScript();
      }, delay);
    } else {
      this.done = true;
      this.loading = false;
      this.onEvent = this.errors[this.errors.length - 1];
      this.callbacks.forEach(cb => {
        cb(this.onEvent);
      });
      this.callbacks = [];
      Loader.loadEventCallback.forEach(cb => {
        cb(this.onEvent);
      });
    }
  }
  createUrl() {
    let url = this.url;
    url += `?appkey=${this.appkey}`;
    if (this.libraries.length) {
      url += `&libraries=${this.libraries.join(",")}`;
    }
    url += `&autoload=false`;
    return url;
  }
  deleteScript() {
    const script = document.getElementById(this.id);
    if (script) {
      script.remove();
    }
  }
  callback() {
    kakao.maps.load(() => {
      this.done = true;
      this.loading = false;
      this.callbacks.forEach(cb => {
        cb(this.onEvent);
      });
      this.callbacks = [];
      Loader.loadEventCallback.forEach(cb => {
        cb(this.onEvent);
      });
    });
  }
  static equalOptions(a, b) {
    if (a.appkey !== b.appkey) return false;
    if (a.id !== b.id) return false;
    if (a.libraries.length !== b.libraries.length) return false;
    for (let i = 0; i < a.libraries.length; ++i) {
      if (a.libraries[i] !== b.libraries[i]) return false;
    }
    if (a.nonce !== b.nonce) return false;
    if (a.retries !== b.retries) return false;
    if (a.url !== b.url) return false;
    return true;
  }
}

exports.Loader = Loader;
exports.LoaderStatus = LoaderStatus;
