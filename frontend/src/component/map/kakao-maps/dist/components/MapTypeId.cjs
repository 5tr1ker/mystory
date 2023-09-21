'use strict';

var React = require('react');
var useMap = require('../hooks/useMap.cjs');

/**
 * 추가적으로 보이고 싶은 지도 타입을 추가 할 때 사용한다.
 */
const MapTypeId = _ref => {
  let {
    type: _type
  } = _ref;
  const map = useMap.useMap(`MapTypeId`);
  const type = typeof _type === "string" ? kakao.maps.MapTypeId[_type] : _type;
  React.useEffect(() => {
    map.addOverlayMapTypeId(type);
    return () => {
      map.removeOverlayMapTypeId(type);
    };
  }, [map, type]);
  return null;
};

exports.MapTypeId = MapTypeId;
