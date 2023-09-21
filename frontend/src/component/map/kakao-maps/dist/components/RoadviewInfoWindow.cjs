'use strict';

var React = require('react');
var InfoWindow = require('./InfoWindow.cjs');
var useRoadview = require('../hooks/useRoadview.cjs');
var jsxRuntime = require('react/jsx-runtime');

/**
 * Map 컴포넌트에서 InfoWindow를 그릴 때 사용됩니다.
 * `onCreate` 이벤트를 통해 생성 후 `infoWindow` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
const RoadviewInfoWindow = /*#__PURE__*/React.forwardRef(function RoadviewInfoWindow(_ref, ref) {
  let {
    position,
    children,
    altitude,
    disableAutoPan,
    range,
    removable,
    zIndex,
    onCreate
  } = _ref;
  const roadview = useRoadview.useRoadview(`RoadviewInfoWindow`);
  const infoPosition = React.useMemo(() => {
    if ("lat" in position) {
      return new kakao.maps.LatLng(position.lat, position.lng);
    }
    return new kakao.maps.Viewpoint(position.pan, position.tilt, position.zoom, position.panoId);
    /* eslint-disable react-hooks/exhaustive-deps*/
    /* eslint-disable @typescript-eslint/ban-ts-comment*/
  }, [
  // @ts-ignore
  position.lat,
  // @ts-ignore
  position.lng,
  // @ts-ignore
  position.pan,
  // @ts-ignore
  position.tilt,
  // @ts-ignore
  position.zoom,
  // @ts-ignore
  position.panoId]);
  /* eslint-enable @typescript-eslint/ban-ts-comment*/
  /* eslint-enable react-hooks/exhaustive-deps */

  return /*#__PURE__*/jsxRuntime.jsx(InfoWindow.InfoWindow, {
    altitude: altitude,
    disableAutoPan: disableAutoPan,
    range: range,
    removable: removable,
    zIndex: zIndex,
    map: roadview,
    position: infoPosition,
    onCreate: onCreate,
    ref: ref,
    children: children
  });
});

exports.RoadviewInfoWindow = RoadviewInfoWindow;
