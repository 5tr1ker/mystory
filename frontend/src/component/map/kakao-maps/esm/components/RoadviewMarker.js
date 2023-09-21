import React, { useMemo } from 'react';
import { useRoadview } from '../hooks/useRoadview.js';
import { Marker } from './Marker.js';
import { jsx } from 'react/jsx-runtime.js';

/**
 * Map에서 Marker를 생성할 때 사용 합니다.
 * `onCreate` 이벤트를 통해 생성 후 `maker` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
const RoadviewMarker = /*#__PURE__*/React.forwardRef(function RoadviewMarker(_ref, ref) {
  let {
    position,
    ...args
  } = _ref;
  const roadview = useRoadview(`RoadviewMarker`);
  const markerPosition = useMemo(() => {
    if ("lat" in position) {
      return new kakao.maps.LatLng(position.lat, position.lng);
    }
    if ("x" in position) {
      return new kakao.maps.Coords(position.x, position.y).toLatLng();
    }
    return new kakao.maps.Viewpoint(position.pan, position.tilt, position.zoom, position.panoId);

    /* eslint-disable react-hooks/exhaustive-deps */
    /*  eslint-disable @typescript-eslint/ban-ts-comment */
  }, [
  // @ts-ignore
  position.lat,
  // @ts-ignore
  position.lng,
  // @ts-ignore
  position.x,
  // @ts-ignore
  position.y,
  // @ts-ignore
  position.pan,
  // @ts-ignore
  position.tilt,
  // @ts-ignore
  position.zoom,
  // @ts-ignore
  position?.panoId]);
  /* eslint-enable @typescript-eslint/ban-ts-comment */
  /* eslint-enable react-hooks/exhaustive-deps */

  return /*#__PURE__*/jsx(Marker, {
    map: roadview,
    position: markerPosition,
    ...args,
    ref: ref
  });
});

export { RoadviewMarker };
