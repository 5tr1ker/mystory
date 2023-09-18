import React, { useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';

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
  const map = useMap(`Circle`);
  const circleCenter = useMemo(() => {
    return new kakao.maps.LatLng(center.lat, center.lng);
  }, [center.lat, center.lng]);
  const circle = useMemo(() => {
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
  useImperativeHandle(ref, () => circle, [circle]);
  useLayoutEffect(() => {
    circle.setMap(map);
    return () => {
      circle.setMap(null);
    };
  }, [map, circle]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(circle);
  }, [circle, onCreate]);
  useLayoutEffect(() => {
    if (circle) circle.setPosition(circleCenter);
  }, [circle, circleCenter]);
  useLayoutEffect(() => {
    circle.setRadius(radius);
  }, [circle, radius]);
  useLayoutEffect(() => {
    if (!zIndex) return;
    circle.setZIndex(zIndex);
  }, [circle, zIndex]);
  useLayoutEffect(() => {
    circle.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [circle, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useKakaoEvent(circle, "mouseover", onMouseover);
  useKakaoEvent(circle, "mouseout", onMouseout);
  useKakaoEvent(circle, "mousemove", onMousemove);
  useKakaoEvent(circle, "mousedown", onMousedown);
  useKakaoEvent(circle, "click", onClick);
  return null;
});

export { Circle };
