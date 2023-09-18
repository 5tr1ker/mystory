'use strict';

var React = require('react');
var Roadview = require('../components/Roadview.cjs');

/**
 * kakao roadview 객체를 가져오는 hook 입니다.
 * Roadview 객체 내부가 아니라면 Error를 발생 시킵니다.
 */
const useRoadview = componentName => {
  const kakaoRoadview = React.useContext(Roadview.KakaoRoadviewContext);
  if (!kakaoRoadview) {
    throw new Error(`${componentName ? componentName + " Component" : "useRoadview"} must exist inside Roadview Component!`);
  }
  return kakaoRoadview;
};

exports.useRoadview = useRoadview;
