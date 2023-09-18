'use strict';

var React = require('react');
var kakaoMapApiLoader = require('../util/kakaoMapApiLoader.cjs');

/**
 * Kakao Map Api를 Loading 합니다.
 *
 * 해당 hook은 cleanup 시점에도 Kakao Map Api를 제거 하지 않고, 동일한 hook를 여러 위치에서 호출 하더라도 최초 한번만 Loading 됩니다.
 *
 * 내부에서 반환하는 `loading` state는 hook를 통해 제어할 때 사용하도록 제공하는 state 입니다.
 *
 * loading를 통한 `Map` 컴포넌트를 conditional rendering를 하지 않아도 됩니다.
 */
const useKakaoLoader = options => {
  const [state, setState] = React.useState([true, undefined]);
  const loader = React.useMemo(() => new kakaoMapApiLoader.Loader(options),
  // eslint-disable-next-line react-hooks/exhaustive-deps
  [JSON.stringify(options)]);
  React.useEffect(() => {
    loader.load().then(() => setState([false, undefined])).catch(error => {
      setState([false, error]);
    });
  }, [loader]);
  return state;
};

exports.useKakaoLoader = useKakaoLoader;
