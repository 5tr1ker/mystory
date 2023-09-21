'use strict';

var useIsomorphicLayoutEffect = require('./useIsomorphicLayoutEffect.cjs');

const useKakaoEvent = (target, type, callback) => {
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (!target || !callback) return;
    const wrapCallback = function () {
      for (var _len = arguments.length, arg = new Array(_len), _key = 0; _key < _len; _key++) {
        arg[_key] = arguments[_key];
      }
      if (arg === undefined) return callback(target);else return callback(target, ...arg);
    };
    kakao.maps.event.addListener(target, type, wrapCallback);
    return () => {
      kakao.maps.event.removeListener(target, type, wrapCallback);
    };
  }, [target, type, callback]);
};

exports.useKakaoEvent = useKakaoEvent;
