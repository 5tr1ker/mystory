import React, { useState, useRef, useImperativeHandle } from 'react';
import { useIsomorphicLayoutEffect } from '../hooks/useIsomorphicLayoutEffect.js';
import { Loader } from '../util/kakaoMapApiLoader.js';
import { jsx } from 'react/jsx-runtime.js';

const StaticMap = /*#__PURE__*/React.forwardRef(function StaticMap(_ref, ref) {
  let {
    as,
    id,
    center,
    marker,
    level,
    mapTypeId,
    onCreate,
    ...props
  } = _ref;
  const Container = as || "div";
  const [isLoaded, setIsLoaded] = useState(false);
  const [map, setMap] = useState();
  const container = useRef(null);
  useIsomorphicLayoutEffect(() => {
    const callback = Loader.addLoadEventLisnter(err => setIsLoaded(!err));
    return () => {
      Loader.removeLoadEventLisnter(callback);
    };
  }, []);
  useIsomorphicLayoutEffect(() => {
    if (!isLoaded) return;
    const MapContainer = container.current;
    if (!MapContainer) return;
    const _marker = (() => {
      if (Array.isArray(marker)) {
        return marker.map(mk => {
          return {
            ...mk,
            position: new kakao.maps.LatLng(mk.position.lat, mk.position.lng)
          };
        });
      }
      if (typeof marker === "object") {
        if (marker.position) {
          return {
            ...marker,
            position: new kakao.maps.LatLng(marker.position.lat, marker.position.lng)
          };
        }
        return marker;
      }
      return marker;
    })();
    const kakaoStaticMap = new kakao.maps.StaticMap(MapContainer, {
      center: new kakao.maps.LatLng(center.lat, center.lng),
      level,
      mapTypeId: typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId,
      marker: _marker
    });
    setMap(kakaoStaticMap);
    return () => {
      MapContainer.innerHTML = "";
    };
  }, [isLoaded, JSON.stringify(marker)]);
  useImperativeHandle(ref, () => map, [map]);
  useIsomorphicLayoutEffect(() => {
    if (map && onCreate) onCreate(map);
  }, [map, onCreate]);
  useIsomorphicLayoutEffect(() => {
    if (map) map.setCenter(new kakao.maps.LatLng(center.lat, center.lng));
  }, [map, center.lat, center.lng]);
  useIsomorphicLayoutEffect(() => {
    if (map && level) map.setLevel(level);
  }, [map, level]);
  useIsomorphicLayoutEffect(() => {
    if (map && mapTypeId) map.setMapTypeId(typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId);
  }, [map, mapTypeId]);
  return /*#__PURE__*/jsx(Container, {
    id: id,
    ...props,
    ref: container
  });
});

export { StaticMap };
