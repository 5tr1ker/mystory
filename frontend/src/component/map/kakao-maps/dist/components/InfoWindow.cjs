'use strict';

var React = require('react');
var ReactDOM = require('react-dom');

const InfoWindow = /*#__PURE__*/React.forwardRef(function InfoWindow(_ref, ref) {
  let {
    map,
    position,
    marker,
    children,
    altitude,
    disableAutoPan,
    range,
    removable,
    zIndex,
    onCreate
  } = _ref;
  const container = React.useRef(document.createElement("div"));
  const infoWindow = React.useMemo(() => {
    const kakaoInfoWindow = new kakao.maps.InfoWindow({
      altitude: altitude,
      disableAutoPan: disableAutoPan,
      range: range,
      removable: removable,
      zIndex: zIndex,
      content: container.current,
      position: position
    });
    container.current.style.display = "none";
    return kakaoInfoWindow;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [disableAutoPan, removable]);
  React.useImperativeHandle(ref, () => infoWindow, [infoWindow]);
  React.useLayoutEffect(() => {
    infoWindow.open(map, marker);
    return () => {
      infoWindow.close();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, marker]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(infoWindow);
  }, [infoWindow, onCreate]);
  React.useLayoutEffect(() => {
    if (!infoWindow) return;
    infoWindow.setPosition(position);
  }, [infoWindow, position]);
  React.useLayoutEffect(() => {
    if (!infoWindow || !altitude) return;
    infoWindow.setAltitude(altitude);
  }, [infoWindow, altitude]);
  React.useLayoutEffect(() => {
    if (!infoWindow || !range) return;
    infoWindow.setRange(range);
  }, [infoWindow, range]);
  React.useLayoutEffect(() => {
    if (!infoWindow || !zIndex) return;
    infoWindow.setZIndex(zIndex);
  }, [infoWindow, zIndex]);
  return container.current.parentElement && /*#__PURE__*/ReactDOM.createPortal(children, container.current.parentElement);
});

exports.InfoWindow = InfoWindow;
