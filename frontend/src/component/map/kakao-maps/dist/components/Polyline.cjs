'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');

/**
 * Map 상에 폴리라인을 그립니다.
 */
const Polyline = /*#__PURE__*/React.forwardRef(function Polyline(_ref, ref) {
  let {
    path,
    endArrow,
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
    zIndex
  } = _ref;
  const map = useMap.useMap(`Polyline`);
  const polyLinePath = React.useMemo(() => {
    if (path.every(v => v instanceof Array)) {
      return path.map(v => {
        return v.map(p => new kakao.maps.LatLng(p.lat, p.lng));
      });
    }
    return path.map(v => {
      return new kakao.maps.LatLng(v.lat, v.lng);
    });
  }, [path]);
  const polyline = React.useMemo(() => {
    return new kakao.maps.Polyline({
      path: polyLinePath,
      endArrow,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight,
      zIndex
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  React.useImperativeHandle(ref, () => polyline, [polyline]);
  React.useLayoutEffect(() => {
    polyline.setMap(map);
    return () => polyline.setMap(null);
  }, [map, polyline]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(polyline);
  }, [polyline, onCreate]);
  React.useLayoutEffect(() => {
    polyline.setOptions({
      endArrow,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [polyline, endArrow, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  React.useLayoutEffect(() => {
    polyline.setPath(polyLinePath);
  }, [polyline, polyLinePath]);
  React.useLayoutEffect(() => {
    if (zIndex) polyline.setZIndex(zIndex);
  }, [polyline, zIndex]);
  useKakaoEvent.useKakaoEvent(polyline, "mouseover", onMouseover);
  useKakaoEvent.useKakaoEvent(polyline, "mouseout", onMouseout);
  useKakaoEvent.useKakaoEvent(polyline, "mousemove", onMousemove);
  useKakaoEvent.useKakaoEvent(polyline, "mousedown", onMousedown);
  useKakaoEvent.useKakaoEvent(polyline, "click", onClick);
  return null;
});

exports.Polyline = Polyline;
