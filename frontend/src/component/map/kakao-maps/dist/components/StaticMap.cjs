'use strict';

var React = require('react');
var useIsomorphicLayoutEffect = require('../hooks/useIsomorphicLayoutEffect.cjs');
var kakaoMapApiLoader = require('../util/kakaoMapApiLoader.cjs');
var jsxRuntime = require('react/jsx-runtime');

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
  const [isLoaded, setIsLoaded] = React.useState(false);
  const [map, setMap] = React.useState();
  const container = React.useRef(null);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    const callback = kakaoMapApiLoader.Loader.addLoadEventLisnter(err => setIsLoaded(!err));
    return () => {
      kakaoMapApiLoader.Loader.removeLoadEventLisnter(callback);
    };
  }, []);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
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
  React.useImperativeHandle(ref, () => map, [map]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (map && onCreate) onCreate(map);
  }, [map, onCreate]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (map) map.setCenter(new kakao.maps.LatLng(center.lat, center.lng));
  }, [map, center.lat, center.lng]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (map && level) map.setLevel(level);
  }, [map, level]);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    if (map && mapTypeId) map.setMapTypeId(typeof mapTypeId === "string" ? kakao.maps.MapTypeId[mapTypeId] : mapTypeId);
  }, [map, mapTypeId]);
  return /*#__PURE__*/jsxRuntime.jsx(Container, {
    id: id,
    ...props,
    ref: container
  });
});

exports.StaticMap = StaticMap;
