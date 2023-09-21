'use strict';

var React = require('react');
var useMap = require('../hooks/useMap.cjs');
var Marker = require('./Marker.cjs');
var jsxRuntime = require('react/jsx-runtime');

/**
 * Map에서 Marker를 생성할 때 사용 합니다.
 * `onCreate` 이벤트를 통해 생성 후 `maker` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
const MapMarker = /*#__PURE__*/React.forwardRef(function MapMarker(_ref, ref) {
  let {
    position,
    ...args
  } = _ref;
  const map = useMap.useMap(`MapMarker`);
  const markerPosition = React.useMemo(() => {
    if ("lat" in position) {
      return new kakao.maps.LatLng(position.lat, position.lng);
    }
    return new kakao.maps.Coords(position.x, position.y).toLatLng();

    // eslint-disable-next-line
    // @ts-ignore
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [position.lat, position.lng, position.x, position.y]);
  return /*#__PURE__*/jsxRuntime.jsx(Marker.Marker, {
    map: map,
    position: markerPosition,
    ...args,
    ref: ref
  });
});

exports.MapMarker = MapMarker;
