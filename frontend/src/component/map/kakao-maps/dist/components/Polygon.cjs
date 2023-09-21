'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');

/**
 * Map 상에 다각형을 그립니다.
 */
const Polygon = /*#__PURE__*/React.forwardRef(function Polygon(_ref, ref) {
  let {
    path,
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
  const map = useMap.useMap(`Polygon`);
  const polygonPath = React.useMemo(() => {
    if (path.every(v => v instanceof Array)) {
      return path.map(v => {
        return v.map(p => new kakao.maps.LatLng(p.lat, p.lng));
      });
    }
    return path.map(v => {
      return new kakao.maps.LatLng(v.lat, v.lng);
    });
  }, [path]);
  const polygon = React.useMemo(() => {
    return new kakao.maps.Polygon({
      path: polygonPath,
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
  React.useImperativeHandle(ref, () => polygon, [polygon]);
  React.useLayoutEffect(() => {
    polygon.setMap(map);
    return () => polygon.setMap(null);
  }, [map, polygon]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(polygon);
  }, [polygon, onCreate]);
  React.useLayoutEffect(() => {
    polygon.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [polygon, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  React.useLayoutEffect(() => {
    polygon.setPath(polygonPath);
  }, [polygon, polygonPath]);
  React.useLayoutEffect(() => {
    if (zIndex) polygon.setZIndex(zIndex);
  }, [polygon, zIndex]);
  useKakaoEvent.useKakaoEvent(polygon, "mouseover", onMouseover);
  useKakaoEvent.useKakaoEvent(polygon, "mouseout", onMouseout);
  useKakaoEvent.useKakaoEvent(polygon, "mousemove", onMousemove);
  useKakaoEvent.useKakaoEvent(polygon, "mousedown", onMousedown);
  useKakaoEvent.useKakaoEvent(polygon, "click", onClick);
  return null;
});

exports.Polygon = Polygon;
