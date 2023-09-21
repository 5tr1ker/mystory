'use strict';

var React = require('react');
var useKakaoEvent = require('../hooks/useKakaoEvent.cjs');
var useMap = require('../hooks/useMap.cjs');
var useIsomorphicLayoutEffect = require('../hooks/useIsomorphicLayoutEffect.cjs');
var jsxRuntime = require('react/jsx-runtime');

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
  const map = useMap.useMap(`MarkerClusterer`);
  const markerClusterer = React.useMemo(() => {
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
  React.useImperativeHandle(ref, () => markerClusterer, [markerClusterer]);
  React.useLayoutEffect(() => {
    markerClusterer?.setMap(map);
  }, [map, markerClusterer]);
  React.useLayoutEffect(() => {
    if (markerClusterer && onCreate) onCreate(markerClusterer);
  }, [markerClusterer, onCreate]);
  React.useLayoutEffect(() => {
    if (markerClusterer && gridSize) {
      markerClusterer.setGridSize(gridSize);
      markerClusterer.redraw();
    }
  }, [markerClusterer, gridSize]);
  React.useLayoutEffect(() => {
    if (markerClusterer && minClusterSize) {
      markerClusterer.setMinClusterSize(minClusterSize);
      markerClusterer.redraw();
    }
  }, [markerClusterer, minClusterSize]);
  React.useLayoutEffect(() => {
    if (markerClusterer && typeof averageCenter !== "undefined") {
      markerClusterer.setAverageCenter(averageCenter);
      markerClusterer.redraw();
    }
  }, [markerClusterer, averageCenter]);
  React.useLayoutEffect(() => {
    if (markerClusterer && minLevel) {
      markerClusterer.setMinLevel(minLevel);
      markerClusterer.redraw();
    }
  }, [markerClusterer, minLevel]);
  React.useLayoutEffect(() => {
    if (markerClusterer && texts) {
      markerClusterer.setTexts(texts);
      markerClusterer.redraw();
    }
  }, [markerClusterer, texts]);
  React.useLayoutEffect(() => {
    if (markerClusterer && calculator) {
      markerClusterer.setCalculator(calculator);
      markerClusterer.redraw();
    }
  }, [markerClusterer, calculator]);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clustered", onClustered);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clusterclick", onClusterclick);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clusterover", onClusterover);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clusterout", onClusterout);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clusterdblclick", onClusterdblclick);
  useKakaoEvent.useKakaoEvent(markerClusterer, "clusterrightclick", onClusterrightclick);
  React.useLayoutEffect(() => {
    if (markerClusterer && styles) {
      markerClusterer.setStyles(styles);
      markerClusterer.redraw();
    }
  }, [markerClusterer, styles]);
  if (!markerClusterer) {
    return null;
  }
  return /*#__PURE__*/jsxRuntime.jsxs(KakaoMapMarkerClustererContext.Provider, {
    value: markerClusterer,
    children: [children, /*#__PURE__*/jsxRuntime.jsx(MarkerClustererRedraw, {
      children: children
    })]
  });
});
const MarkerClustererRedraw = _ref2 => {
  const markerClusterer = React.useContext(KakaoMapMarkerClustererContext);
  useIsomorphicLayoutEffect.useIsomorphicLayoutEffect(() => {
    markerClusterer.redraw();
  });
  return null;
};

exports.KakaoMapMarkerClustererContext = KakaoMapMarkerClustererContext;
exports.MarkerClusterer = MarkerClusterer;
