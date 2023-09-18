'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');

/**
 * Map 상에 사각형을 그립니다.
 */
const Rectangle = /*#__PURE__*/React.forwardRef(function Rectangle(_ref, ref) {
  let {
    bounds,
    onClick,
    onMousedown,
    onMousemove,
    onMouseout,
    onMouseover,
    onCreate,
    strokeColor,
    strokeOpacity,
    strokeStyle,
    strokeWeight,
    fillColor,
    fillOpacity,
    zIndex
  } = _ref;
  const map = useMap.useMap(`Rectangle`);
  const rectangleBounds = React.useMemo(() => {
    return new kakao.maps.LatLngBounds(new kakao.maps.LatLng(bounds.sw.lat, bounds.sw.lng), new kakao.maps.LatLng(bounds.ne.lat, bounds.ne.lng));
  }, [bounds]);
  const rectangle = React.useMemo(() => {
    return new kakao.maps.Rectangle({
      bounds: rectangleBounds,
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
  React.useImperativeHandle(ref, () => rectangle, [rectangle]);
  React.useLayoutEffect(() => {
    rectangle.setMap(map);
    return () => rectangle.setMap(null);
  }, [map, rectangle]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(rectangle);
  }, [rectangle, onCreate]);
  React.useLayoutEffect(() => {
    rectangle.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [rectangle, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  React.useLayoutEffect(() => {
    rectangle.setBounds(rectangleBounds);
  }, [rectangle, rectangleBounds]);
  React.useLayoutEffect(() => {
    if (zIndex) rectangle.setZIndex(zIndex);
  }, [rectangle, zIndex]);
  useKakaoEvent.useKakaoEvent(rectangle, "mouseover", onMouseover);
  useKakaoEvent.useKakaoEvent(rectangle, "mouseout", onMouseout);
  useKakaoEvent.useKakaoEvent(rectangle, "mousemove", onMousemove);
  useKakaoEvent.useKakaoEvent(rectangle, "mousedown", onMousedown);
  useKakaoEvent.useKakaoEvent(rectangle, "click", onClick);
  return null;
});

exports.Rectangle = Rectangle;
