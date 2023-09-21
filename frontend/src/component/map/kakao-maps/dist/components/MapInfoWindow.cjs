'use strict';

var React = require('react');
var InfoWindow = require('./InfoWindow.cjs');
var useMap = require('../hooks/useMap.cjs');
var jsxRuntime = require('react/jsx-runtime');

/**
 * Map 컴포넌트에서 InfoWindow를 그릴 때 사용됩니다.
 * `onCreate` 이벤트를 통해 생성 후 `infoWindow` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
const MapInfoWindow = /*#__PURE__*/React.forwardRef(function MapInfoWindow(_ref, ref) {
  let {
    position,
    children,
    disableAutoPan,
    removable,
    zIndex,
    onCreate
  } = _ref;
  const map = useMap.useMap(`MapInfoWindow`);
  const infoPosition = React.useMemo(() => {
    return new kakao.maps.LatLng(position.lat, position.lng);
  }, [position.lat, position.lng]);
  return /*#__PURE__*/jsxRuntime.jsx(InfoWindow.InfoWindow, {
    disableAutoPan: disableAutoPan,
    removable: removable,
    zIndex: zIndex,
    map: map,
    position: infoPosition,
    onCreate: onCreate,
    ref: ref,
    children: children
  });
});

exports.MapInfoWindow = MapInfoWindow;
