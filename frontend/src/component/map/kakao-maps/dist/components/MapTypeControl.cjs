'use strict';

var React = require('react');
var useMap = require('../hooks/useMap.cjs');

/**
 * 일반 지도/하이브리드 간 지도 타입 전환 컨트롤을 생성한다.
 * 현재는 일반 지도/하이브리드 간 전환 컨트롤만 지원되며 다른 지도 타입을 제어하는 컨트롤이 필요할 경우에는 직접 구현해야 한다.
 */
const MapTypeControl = /*#__PURE__*/React.forwardRef(function MapTypeControl(_ref, ref) {
  let {
    position: _position = kakao.maps.ControlPosition.TOPRIGHT
  } = _ref;
  const map = useMap.useMap(`MapTypeControl`);
  const position = typeof _position === "string" ? kakao.maps.ControlPosition[_position] : _position;
  const mapTypeControl = React.useMemo(() => {
    return new kakao.maps.MapTypeControl();
  }, []);
  React.useImperativeHandle(ref, () => mapTypeControl, [mapTypeControl]);
  React.useLayoutEffect(() => {
    map.addControl(mapTypeControl, position);
    return () => {
      map.removeControl(position);
    };
  }, [map, mapTypeControl, position]);
  return null;
});

exports.MapTypeControl = MapTypeControl;
