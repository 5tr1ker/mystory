import React, { useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';

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
  const map = useMap(`Rectangle`);
  const rectangleBounds = useMemo(() => {
    return new kakao.maps.LatLngBounds(new kakao.maps.LatLng(bounds.sw.lat, bounds.sw.lng), new kakao.maps.LatLng(bounds.ne.lat, bounds.ne.lng));
  }, [bounds]);
  const rectangle = useMemo(() => {
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
  useImperativeHandle(ref, () => rectangle, [rectangle]);
  useLayoutEffect(() => {
    rectangle.setMap(map);
    return () => rectangle.setMap(null);
  }, [map, rectangle]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(rectangle);
  }, [rectangle, onCreate]);
  useLayoutEffect(() => {
    rectangle.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [rectangle, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useLayoutEffect(() => {
    rectangle.setBounds(rectangleBounds);
  }, [rectangle, rectangleBounds]);
  useLayoutEffect(() => {
    if (zIndex) rectangle.setZIndex(zIndex);
  }, [rectangle, zIndex]);
  useKakaoEvent(rectangle, "mouseover", onMouseover);
  useKakaoEvent(rectangle, "mouseout", onMouseout);
  useKakaoEvent(rectangle, "mousemove", onMousemove);
  useKakaoEvent(rectangle, "mousedown", onMousedown);
  useKakaoEvent(rectangle, "click", onClick);
  return null;
});

export { Rectangle };
