'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var InfoWindow = require('./InfoWindow.cjs');
var MarkerClusterer = require('./MarkerClusterer.cjs');
var jsxRuntime = require('react/jsx-runtime');

const Marker = /*#__PURE__*/React.forwardRef(function Marker(_ref, ref) {
  let {
    map,
    position,
    children,
    altitude,
    clickable,
    draggable,
    image,
    infoWindowOptions,
    onCreate,
    onClick,
    onDragEnd,
    onDragStart,
    onMouseOut,
    onMouseOver,
    opacity,
    range,
    title,
    zIndex
  } = _ref;
  const markerCluster = React.useContext(MarkerClusterer.KakaoMapMarkerClustererContext);
  const markerImage = React.useMemo(() => {
    return image && new kakao.maps.MarkerImage(image.src, new kakao.maps.Size(image.size.width, image.size.height), {
      alt: image.options?.alt,
      coords: image.options?.coords,
      offset: image.options?.offset && new kakao.maps.Point(image.options?.offset.x, image.options?.offset.y),
      shape: image.options?.shape,
      spriteOrigin: image.options?.spriteOrigin && new kakao.maps.Point(image.options?.spriteOrigin.x, image.options?.spriteOrigin.y),
      spriteSize: image.options?.spriteSize && new kakao.maps.Size(image.options?.spriteSize.width, image.options?.spriteSize.height)
    });

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [JSON.stringify(image)]);
  const marker = React.useMemo(() => {
    const kakaoMarker = new kakao.maps.Marker({
      altitude,
      clickable,
      draggable,
      image: markerImage,
      opacity,
      range,
      title,
      zIndex,
      position
    });
    return kakaoMarker;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  React.useImperativeHandle(ref, () => marker, [marker]);
  React.useLayoutEffect(() => {
    if (markerCluster) {
      markerCluster.addMarker(marker, true);
    } else {
      marker.setMap(map);
    }
    return () => {
      if (markerCluster) {
        markerCluster.removeMarker(marker, true);
      } else {
        marker.setMap(null);
      }
    };
  }, [map, markerCluster, marker]);
  React.useLayoutEffect(() => {
    if (onCreate) onCreate(marker);
  }, [marker, onCreate]);
  useKakaoEvent.useKakaoEvent(marker, "click", onClick);
  useKakaoEvent.useKakaoEvent(marker, "dragstart", onDragStart);
  useKakaoEvent.useKakaoEvent(marker, "dragend", onDragEnd);
  useKakaoEvent.useKakaoEvent(marker, "mouseout", onMouseOut);
  useKakaoEvent.useKakaoEvent(marker, "mouseover", onMouseOver);

  // position이 변경되면 객체를 갱신한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || !position) return;
    marker.setPosition(position);
  }, [map, marker, position]);

  // image 객체가 존재하면 이미지를 로드한다
  React.useLayoutEffect(() => {
    if (!map || !marker || !markerImage) return;
    marker.setImage(markerImage);
  }, [map, marker, markerImage]);

  // altitude 값이 있으면 높이를 조정한다
  React.useLayoutEffect(() => {
    if (!map || !marker || !altitude) return;
    marker.setAltitude(altitude);
  }, [map, marker, altitude]);

  // clickable 값이 있으면 클릭이 가능하도록 한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || typeof clickable === "undefined") return;
    marker.setClickable(clickable);
  }, [map, marker, clickable]);

  // draggable 값이 있으면 드래그가 가능하도록 한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || typeof draggable === "undefined") return;
    marker.setDraggable(draggable);
  }, [map, marker, draggable]);

  // opacity 값이 있으면 투명도를 조절한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || !opacity) return;
    marker.setOpacity(opacity);
  }, [map, marker, opacity]);

  // range 값이 있으면 마커의 가시반경을 조절한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || !range) return;
    marker.setRange(range);
  }, [map, marker, range]);

  // title 값이 있으면 마커의 제목을 조절한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || !title) return;
    marker.setTitle(title);
  }, [map, marker, title]);

  // zIndex 값이 있으면 마커의 zindex를 조절한다.
  React.useLayoutEffect(() => {
    if (!map || !marker || !zIndex) return;
    marker.setZIndex(zIndex);
  }, [map, marker, zIndex]);
  if (children) return /*#__PURE__*/jsxRuntime.jsx(InfoWindow.InfoWindow, {
    position: position,
    map: map,
    marker: marker,
    altitude: infoWindowOptions?.altitude,
    disableAutoPan: infoWindowOptions?.disableAutoPan,
    range: infoWindowOptions?.range,
    removable: infoWindowOptions?.removable,
    zIndex: infoWindowOptions?.zIndex,
    children: children
  });
  return null;
});

exports.Marker = Marker;
