'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');

/**
 * Map 상에 원을 그립니다.
 */
const Circle = /*#__PURE__*/React.forwardRef(function Circle(_ref, ref) {
  let {
    center,
    radius,
    fillColor,
    fillOpacity,
    strokeColor,
    strokeOpacity,
    strokeStyle,
    strokeWeight,
    zIndex,
    onMouseover,
    onMouseout,
    onMousemove,
    onMousedown,
    onClick,
    onCreate
  } = _ref;
  const map = useMap.useMap(`Circle`);
  const circleCenter = React.useMemo(() => {
    return new kakao.maps.LatLng(center.lat, center.lng);
  }, [center.lat, center.lng]);
  const circle = React.useMemo(() => {
    return new kakao.maps.Circle({
      center: circleCenter,
      radius,
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight,
      zIndex
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  React.useImperativeHandle(ref, () => circle, [circle]);
  React.useLayoutEffect(() => {
    circle.setMap(map);
    return () => {
      circle.setMap(null);
    };
  }, [map, circle]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(circle);
  }, [circle, onCreate]);
  React.useLayoutEffect(() => {
    if (circle) circle.setPosition(circleCenter);
  }, [circle, circleCenter]);
  React.useLayoutEffect(() => {
    circle.setRadius(radius);
  }, [circle, radius]);
  React.useLayoutEffect(() => {
    if (!zIndex) return;
    circle.setZIndex(zIndex);
  }, [circle, zIndex]);
  React.useLayoutEffect(() => {
    circle.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [circle, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useKakaoEvent.useKakaoEvent(circle, "mouseover", onMouseover);
  useKakaoEvent.useKakaoEvent(circle, "mouseout", onMouseout);
  useKakaoEvent.useKakaoEvent(circle, "mousemove", onMousemove);
  useKakaoEvent.useKakaoEvent(circle, "mousedown", onMousedown);
  useKakaoEvent.useKakaoEvent(circle, "click", onClick);
  return null;
});

exports.Circle = Circle;
