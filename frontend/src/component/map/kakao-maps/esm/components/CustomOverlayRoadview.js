import React, { useRef, useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import ReactDOM from 'react-dom';
import { useRoadview } from '../hooks/useRoadview.js';

/**
 * Roadview에 CustomOverlay를 올릴 때 사용하는 컴포넌트 입니다.
 * `onCreate` 이벤트 또는 `ref` 객체를 통해서 `CustomOverlay` 객체에 직접 접근 및 초기 설정 작업을 지정할 수 있습니다.
 */
const CustomOverlayRoadview = /*#__PURE__*/React.forwardRef(function CustomOverlayRoadview(_ref, ref) {
  let {
    position,
    children,
    clickable,
    xAnchor,
    yAnchor,
    zIndex,
    altitude,
    range,
    onCreate
  } = _ref;
  const roadview = useRoadview(`CustomOverlayRoadview`);
  const container = useRef(document.createElement("div"));
  const overlayPosition = useMemo(() => {
    if ("lat" in position) {
      return new kakao.maps.LatLng(position.lat, position.lng);
    }
    return new kakao.maps.Viewpoint(position.pan, position.tilt, position.zoom, position.panoId);
    /* eslint-disable react-hooks/exhaustive-deps */
  }, [/* eslint-disable @typescript-eslint/ban-ts-comment */
  /* @ts-ignore */
  position.lat, /* @ts-ignore */
  position.lng, /* @ts-ignore */
  position.pan, /* @ts-ignore */
  position.tilt, /* @ts-ignore */
  position.zoom, /* @ts-ignore */
  position.panoId
  /* eslint-enable @typescript-eslint/ban-ts-comment */]);
  /* eslint-enable react-hooks/exhaustive-deps */

  const overlay = useMemo(() => {
    const KakaoCustomOverlay = new kakao.maps.CustomOverlay({
      clickable: clickable,
      xAnchor: xAnchor,
      yAnchor: yAnchor,
      zIndex: zIndex,
      position: overlayPosition,
      content: container.current
    });
    container.current.style.display = "none";
    return KakaoCustomOverlay;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [clickable, xAnchor, yAnchor]);
  useImperativeHandle(ref, () => overlay, [overlay]);
  useLayoutEffect(() => {
    if (!roadview) return;
    overlay.setMap(roadview);
    return () => {
      overlay.setMap(null);
    };
  }, [overlay, roadview]);
  useLayoutEffect(() => {
    if (onCreate) onCreate(overlay);
  }, [overlay, onCreate]);
  useLayoutEffect(() => {
    overlay.setPosition(overlayPosition);
  }, [overlay, overlayPosition]);
  useLayoutEffect(() => {
    if (!zIndex) return;
    overlay.setZIndex(zIndex);
  }, [overlay, zIndex]);
  useLayoutEffect(() => {
    if (!altitude) return;
    overlay.setAltitude(altitude);
  }, [overlay, altitude]);
  useLayoutEffect(() => {
    if (!range) return;
    overlay.setRange(range);
  }, [overlay, range]);
  return container.current.parentElement && /*#__PURE__*/ReactDOM.createPortal(children, container.current.parentElement);
});

export { CustomOverlayRoadview };
