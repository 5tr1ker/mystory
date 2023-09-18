import React, { useRef, useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import ReactDOM from 'react-dom';

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
  const container = useRef(document.createElement("div"));
  const infoWindow = useMemo(() => {
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
  useImperativeHandle(ref, () => infoWindow, [infoWindow]);
  useLayoutEffect(() => {
    infoWindow.open(map, marker);
    return () => {
      infoWindow.close();
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [map, marker]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(infoWindow);
  }, [infoWindow, onCreate]);
  useLayoutEffect(() => {
    if (!infoWindow) return;
    infoWindow.setPosition(position);
  }, [infoWindow, position]);
  useLayoutEffect(() => {
    if (!infoWindow || !altitude) return;
    infoWindow.setAltitude(altitude);
  }, [infoWindow, altitude]);
  useLayoutEffect(() => {
    if (!infoWindow || !range) return;
    infoWindow.setRange(range);
  }, [infoWindow, range]);
  useLayoutEffect(() => {
    if (!infoWindow || !zIndex) return;
    infoWindow.setZIndex(zIndex);
  }, [infoWindow, zIndex]);
  return container.current.parentElement && /*#__PURE__*/ReactDOM.createPortal(children, container.current.parentElement);
});

export { InfoWindow };
