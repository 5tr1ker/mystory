import React, { useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';

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
  const map = useMap(`Polygon`);
  const polygonPath = useMemo(() => {
    if (path.every(v => v instanceof Array)) {
      return path.map(v => {
        return v.map(p => new kakao.maps.LatLng(p.lat, p.lng));
      });
    }
    return path.map(v => {
      return new kakao.maps.LatLng(v.lat, v.lng);
    });
  }, [path]);
  const polygon = useMemo(() => {
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
  useImperativeHandle(ref, () => polygon, [polygon]);
  useLayoutEffect(() => {
    polygon.setMap(map);
    return () => polygon.setMap(null);
  }, [map, polygon]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(polygon);
  }, [polygon, onCreate]);
  useLayoutEffect(() => {
    polygon.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [polygon, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useLayoutEffect(() => {
    polygon.setPath(polygonPath);
  }, [polygon, polygonPath]);
  useLayoutEffect(() => {
    if (zIndex) polygon.setZIndex(zIndex);
  }, [polygon, zIndex]);
  useKakaoEvent(polygon, "mouseover", onMouseover);
  useKakaoEvent(polygon, "mouseout", onMouseout);
  useKakaoEvent(polygon, "mousemove", onMousemove);
  useKakaoEvent(polygon, "mousedown", onMousedown);
  useKakaoEvent(polygon, "click", onClick);
  return null;
});

export { Polygon };
