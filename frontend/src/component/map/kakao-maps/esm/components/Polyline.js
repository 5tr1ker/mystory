import React, { useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';

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
  const map = useMap(`Polyline`);
  const polyLinePath = useMemo(() => {
    if (path.every(v => v instanceof Array)) {
      return path.map(v => {
        return v.map(p => new kakao.maps.LatLng(p.lat, p.lng));
      });
    }
    return path.map(v => {
      return new kakao.maps.LatLng(v.lat, v.lng);
    });
  }, [path]);
  const polyline = useMemo(() => {
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
  useImperativeHandle(ref, () => polyline, [polyline]);
  useLayoutEffect(() => {
    polyline.setMap(map);
    return () => polyline.setMap(null);
  }, [map, polyline]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(polyline);
  }, [polyline, onCreate]);
  useLayoutEffect(() => {
    polyline.setOptions({
      endArrow,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [polyline, endArrow, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useLayoutEffect(() => {
    polyline.setPath(polyLinePath);
  }, [polyline, polyLinePath]);
  useLayoutEffect(() => {
    if (zIndex) polyline.setZIndex(zIndex);
  }, [polyline, zIndex]);
  useKakaoEvent(polyline, "mouseover", onMouseover);
  useKakaoEvent(polyline, "mouseout", onMouseout);
  useKakaoEvent(polyline, "mousemove", onMousemove);
  useKakaoEvent(polyline, "mousedown", onMousedown);
  useKakaoEvent(polyline, "click", onClick);
  return null;
});

export { Polyline };
