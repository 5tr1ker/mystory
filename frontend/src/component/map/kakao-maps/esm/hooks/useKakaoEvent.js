import { useIsomorphicLayoutEffect } from './useIsomorphicLayoutEffect.js';

const useKakaoEvent = (target, type, callback) => {

  const { kakao } = window;
  
  useIsomorphicLayoutEffect(() => {
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

export { useKakaoEvent };
