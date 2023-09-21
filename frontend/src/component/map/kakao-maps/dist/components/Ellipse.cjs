'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');

/**
 * Map 상에 타원을 그립니다.
 */
const Ellipse = /*#__PURE__*/React.forwardRef(function Ellipse(_ref, ref) {
  let {
    center,
    rx,
    ry,
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
  const map = useMap.useMap(`Ellipse`);
  const ellipseCenter = React.useMemo(() => {
    return new kakao.maps.LatLng(center.lat, center.lng);
  }, [center.lat, center.lng]);
  const ellipse = React.useMemo(() => {
    return new kakao.maps.Ellipse({
      center: ellipseCenter,
      rx,
      ry,
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
  React.useImperativeHandle(ref, () => ellipse, [ellipse]);
  React.useLayoutEffect(() => {
    ellipse.setMap(map);
    return () => {
      ellipse.setMap(null);
    };
  }, [map, ellipse]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(ellipse);
  }, [ellipse, onCreate]);
  React.useLayoutEffect(() => {
    if (ellipse) ellipse.setPosition(ellipseCenter);
  }, [ellipse, ellipseCenter]);
  React.useLayoutEffect(() => {
    ellipse.setRadius(rx, ry);
  }, [ellipse, rx, ry]);
  React.useLayoutEffect(() => {
    if (!zIndex) return;
    ellipse.setZIndex(zIndex);
  }, [ellipse, zIndex]);
  React.useLayoutEffect(() => {
    ellipse.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [ellipse, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useKakaoEvent.useKakaoEvent(ellipse, "mouseover", onMouseover);
  useKakaoEvent.useKakaoEvent(ellipse, "mouseout", onMouseout);
  useKakaoEvent.useKakaoEvent(ellipse, "mousemove", onMousemove);
  useKakaoEvent.useKakaoEvent(ellipse, "mousedown", onMousedown);
  useKakaoEvent.useKakaoEvent(ellipse, "click", onClick);
  return null;
});

exports.Ellipse = Ellipse;
