import { useEffect } from 'react';
import { useMap } from '../hooks/useMap.js';

/**
 * 추가적으로 보이고 싶은 지도 타입을 추가 할 때 사용한다.
 */
const MapTypeId = _ref => {
  let {
    type: _type
  } = _ref;
  const map = useMap(`MapTypeId`);
  const type = typeof _type === "string" ? kakao.maps.MapTypeId[_type] : _type;
  useEffect(() => {
    map.addOverlayMapTypeId(type);
    return () => {
      map.removeOverlayMapTypeId(type);
    };
  }, [map, type]);
  return null;
};

export { MapTypeId };
