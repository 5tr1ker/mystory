import React, { useState, useRef, useImperativeHandle } from 'react';
import { useIsomorphicLayoutEffect } from '../hooks/useIsomorphicLayoutEffect.js';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { Loader } from '../util/kakaoMapApiLoader.js';
import { SIGNATURE } from '../util/constants.js';
import { jsxs, Fragment, jsx } from 'react/jsx-runtime.js';

const KakaoRoadviewContext = /*#__PURE__*/React.createContext(undefined);
/**
 * Roadview를 Roadview를 생성하는 컴포넌트 입니다.
 * props로 받는 `on*` 이벤트는 해당 `kakao.maps.Map` 객체를 반환 합니다.
 * `onCreate` 이벤트를 통해 생성 후 `Roadview` 객체에 직접 접근하여 초기 설정이 가능합니다.
 */
const Roadview = /*#__PURE__*/React.forwardRef(function Roadview(_ref, ref) {
  let {
    id,
    as,
    children,
    position,
    pan,
    panoId,
    panoX,
    panoY,
    tilt,
    zoom,
    onCreate,
    onInit,
    onPanoidChange,
    onPositionChanged,
    onViewpointChange,
    onErrorGetNearestPanoId,
    ...props
  } = _ref;
  const Container = as || "div";
  const [isLoaded, setIsLoaded] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [roadview, setRoadview] = useState();
  const container = useRef(null);
  useIsomorphicLayoutEffect(() => {
    const callback = Loader.addLoadEventLisnter(err => setIsLoaded(!err));
    return () => {
      Loader.removeLoadEventLisnter(callback);
    };
  }, []);
  useIsomorphicLayoutEffect(() => {
    if (!isLoaded) return;
    const RoadviewContainer = container.current;
    if (!RoadviewContainer) return;
    const kakaoRoadview = new kakao.maps.Roadview(RoadviewContainer, {
      pan: pan,
      panoId: panoId,
      panoX: panoX,
      panoY: panoY,
      tilt: tilt,
      zoom: zoom
    });
    setRoadview(kakaoRoadview);
    return () => {
      RoadviewContainer.innerHTML = "";
    };
  }, [isLoaded, panoX, panoY, zoom]);
  useImperativeHandle(ref, () => roadview, [roadview]);
  useIsomorphicLayoutEffect(() => {
    if (!roadview || !onCreate) return;
    onCreate(roadview);
  }, [roadview, onCreate]);
  useIsomorphicLayoutEffect(() => {
    if (!roadview || panoId || roadview.getPosition().getLat() === position.lat && roadview.getPosition().getLng() === position.lng) return;
    const newPostion = new kakao.maps.LatLng(position.lat, position.lng);
    new kakao.maps.RoadviewClient().getNearestPanoId(newPostion, position.radius, panoId => {
      if (panoId === null && onErrorGetNearestPanoId) {
        onErrorGetNearestPanoId(roadview);
      } else {
        roadview.setPanoId(panoId, newPostion);
      }
    });
  }, [roadview, panoId, position.lat, position.lng, position.radius, onErrorGetNearestPanoId]);
  useIsomorphicLayoutEffect(() => {
    if (!roadview || !panoId || panoId === roadview.getPanoId() || roadview.getPosition().getLat() === position.lat && roadview.getPosition().getLng() === position.lng) return;
    const newPostion = new kakao.maps.LatLng(position.lat, position.lng);
    roadview.setPanoId(panoId, newPostion);
  }, [roadview, panoId, position.lat, position.lng]);
  useIsomorphicLayoutEffect(() => {
    if (!roadview) return;
    const prevViewpoint = roadview.getViewpoint();
    if (prevViewpoint.pan === pan && prevViewpoint.tilt === tilt) return;
    if (pan) prevViewpoint.pan = pan;
    if (tilt) prevViewpoint.tilt = tilt;
    roadview.setViewpoint(prevViewpoint);
  }, [roadview, pan, tilt]);
  useKakaoEvent(roadview, "init", target => {
    setIsLoading(false);
    if (onInit) onInit(target);
  });
  useKakaoEvent(roadview, "panoid_changed", onPanoidChange);
  useKakaoEvent(roadview, "viewpoint_changed", onViewpointChange);
  useKakaoEvent(roadview, "position_changed", onPositionChanged);
  return /*#__PURE__*/jsxs(Fragment, {
    children: [/*#__PURE__*/jsx(Container, {
      ref: container,
      id: id || `${SIGNATURE}_Roadview`,
      ...props
    }), roadview && !isLoading && /*#__PURE__*/jsx(KakaoRoadviewContext.Provider, {
      value: roadview,
      children: children
    })]
  });
});

export { KakaoRoadviewContext, Roadview };
