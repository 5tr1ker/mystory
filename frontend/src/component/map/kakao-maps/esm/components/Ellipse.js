import React, { useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';

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
  const map = useMap(`Ellipse`);
  const ellipseCenter = useMemo(() => {
    return new kakao.maps.LatLng(center.lat, center.lng);
  }, [center.lat, center.lng]);
  const ellipse = useMemo(() => {
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
  useImperativeHandle(ref, () => ellipse, [ellipse]);
  useLayoutEffect(() => {
    ellipse.setMap(map);
    return () => {
      ellipse.setMap(null);
    };
  }, [map, ellipse]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(ellipse);
  }, [ellipse, onCreate]);
  useLayoutEffect(() => {
    if (ellipse) ellipse.setPosition(ellipseCenter);
  }, [ellipse, ellipseCenter]);
  useLayoutEffect(() => {
    ellipse.setRadius(rx, ry);
  }, [ellipse, rx, ry]);
  useLayoutEffect(() => {
    if (!zIndex) return;
    ellipse.setZIndex(zIndex);
  }, [ellipse, zIndex]);
  useLayoutEffect(() => {
    ellipse.setOptions({
      fillColor,
      fillOpacity,
      strokeColor,
      strokeOpacity,
      strokeStyle,
      strokeWeight
    });
  }, [ellipse, fillColor, fillOpacity, strokeColor, strokeOpacity, strokeStyle, strokeWeight]);
  useKakaoEvent(ellipse, "mouseover", onMouseover);
  useKakaoEvent(ellipse, "mouseout", onMouseout);
  useKakaoEvent(ellipse, "mousemove", onMousemove);
  useKakaoEvent(ellipse, "mousedown", onMousedown);
  useKakaoEvent(ellipse, "click", onClick);
  return null;
});

export { Ellipse };
