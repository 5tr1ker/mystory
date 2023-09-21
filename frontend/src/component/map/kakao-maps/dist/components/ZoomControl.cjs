'use strict';

var React = require('react');
var useMap = require('../hooks/useMap.cjs');

/**
 * 확대·축소 컨트롤을 생성한다.
 */
const ZoomControl = /*#__PURE__*/React.forwardRef(function ZoomControl(_ref, ref) {
  let {
    position: _position = kakao.maps.ControlPosition.RIGHT
  } = _ref;
  const map = useMap.useMap(`ZoomControl`);
  const position = typeof _position === "string" ? kakao.maps.ControlPosition[_position] : _position;
  const ZoomControl = React.useMemo(() => {
    return new kakao.maps.ZoomControl();
  }, []);
  React.useImperativeHandle(ref, () => ZoomControl, [ZoomControl]);
  React.useEffect(() => {
    map.addControl(ZoomControl, position);
    return () => {
      map.removeControl(ZoomControl);
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, position]);
  return null;
});

exports.ZoomControl = ZoomControl;
