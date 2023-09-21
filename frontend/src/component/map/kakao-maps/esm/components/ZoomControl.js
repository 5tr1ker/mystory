import React, { useMemo, useImperativeHandle, useEffect } from 'react';
import { useMap } from '../hooks/useMap.js';

/**
 * 확대·축소 컨트롤을 생성한다.
 */
const ZoomControl = /*#__PURE__*/React.forwardRef(function ZoomControl(_ref, ref) {
  let {
    position: _position = kakao.maps.ControlPosition.RIGHT
  } = _ref;
  const map = useMap(`ZoomControl`);
  const position = typeof _position === "string" ? kakao.maps.ControlPosition[_position] : _position;
  const ZoomControl = useMemo(() => {
    return new kakao.maps.ZoomControl();
  }, []);
  useImperativeHandle(ref, () => ZoomControl, [ZoomControl]);
  useEffect(() => {
    map.addControl(ZoomControl, position);
    return () => {
      map.removeControl(ZoomControl);
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, position]);
  return null;
});

export { ZoomControl };
