import React, { useMemo, useImperativeHandle, useLayoutEffect, useContext } from 'react';
import { useKakaoEvent } from '../hooks/useKakaoEvent.js';
import { useMap } from '../hooks/useMap.js';
import { useIsomorphicLayoutEffect } from '../hooks/useIsomorphicLayoutEffect.js';
import { jsxs, jsx } from 'react/jsx-runtime.js';

const KakaoMapMarkerClustererContext = /*#__PURE__*/React.createContext(undefined);
const MarkerClusterer = /*#__PURE__*/React.forwardRef(function MarkerClusterer(_ref, ref) {
  let {
    children,
    averageCenter,
    calculator,
    clickable,
    disableClickZoom,
    gridSize,
    hoverable,
    minClusterSize,
    minLevel,
    styles,
    texts,
    onClusterclick,
    onClusterdblclick,
    onClustered,
    onClusterout,
    onClusterover,
    onClusterrightclick,
    onCreate
  } = _ref;
  const map = useMap(`MarkerClusterer`);
  const markerClusterer = useMemo(() => {
    if (!window.kakao.maps.MarkerClusterer) {
      console.warn("clusterer 라이브러리를 별도 로드 해야 사용 가능합니다. `https://apis.map.kakao.com/web/guide/#loadlibrary`");
      return;
    }
    return new kakao.maps.MarkerClusterer({
      averageCenter,
      calculator,
      clickable,
      disableClickZoom,
      gridSize,
      hoverable,
      minClusterSize,
      minLevel,
      styles,
      texts
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  useImperativeHandle(ref, () => markerClusterer, [markerClusterer]);
  useLayoutEffect(() => {
    markerClusterer?.setMap(map);
  }, [map, markerClusterer]);
  useLayoutEffect(() => {
    if (markerClusterer && onCreate) onCreate(markerClusterer);
  }, [markerClusterer, onCreate]);
  useLayoutEffect(() => {
    if (markerClusterer && gridSize) {
      markerClusterer.setGridSize(gridSize);
      markerClusterer.redraw();
    }
  }, [markerClusterer, gridSize]);
  useLayoutEffect(() => {
    if (markerClusterer && minClusterSize) {
      markerClusterer.setMinClusterSize(minClusterSize);
      markerClusterer.redraw();
    }
  }, [markerClusterer, minClusterSize]);
  useLayoutEffect(() => {
    if (markerClusterer && typeof averageCenter !== "undefined") {
      markerClusterer.setAverageCenter(averageCenter);
      markerClusterer.redraw();
    }
  }, [markerClusterer, averageCenter]);
  useLayoutEffect(() => {
    if (markerClusterer && minLevel) {
      markerClusterer.setMinLevel(minLevel);
      markerClusterer.redraw();
    }
  }, [markerClusterer, minLevel]);
  useLayoutEffect(() => {
    if (markerClusterer && texts) {
      markerClusterer.setTexts(texts);
      markerClusterer.redraw();
    }
  }, [markerClusterer, texts]);
  useLayoutEffect(() => {
    if (markerClusterer && calculator) {
      markerClusterer.setCalculator(calculator);
      markerClusterer.redraw();
    }
  }, [markerClusterer, calculator]);
  useKakaoEvent(markerClusterer, "clustered", onClustered);
  useKakaoEvent(markerClusterer, "clusterclick", onClusterclick);
  useKakaoEvent(markerClusterer, "clusterover", onClusterover);
  useKakaoEvent(markerClusterer, "clusterout", onClusterout);
  useKakaoEvent(markerClusterer, "clusterdblclick", onClusterdblclick);
  useKakaoEvent(markerClusterer, "clusterrightclick", onClusterrightclick);
  useLayoutEffect(() => {
    if (markerClusterer && styles) {
      markerClusterer.setStyles(styles);
      markerClusterer.redraw();
    }
  }, [markerClusterer, styles]);
  if (!markerClusterer) {
    return null;
  }
  return /*#__PURE__*/jsxs(KakaoMapMarkerClustererContext.Provider, {
    value: markerClusterer,
    children: [children, /*#__PURE__*/jsx(MarkerClustererRedraw, {
      children: children
    })]
  });
});
const MarkerClustererRedraw = _ref2 => {
  const markerClusterer = useContext(KakaoMapMarkerClustererContext);
  useIsomorphicLayoutEffect(() => {
    markerClusterer.redraw();
  });
  return null;
};

export { KakaoMapMarkerClustererContext, MarkerClusterer };
