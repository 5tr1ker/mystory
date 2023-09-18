import React, { useContext, useMemo, useImperativeHandle, useLayoutEffect } from 'react';
import { useMap } from '../hooks/useMap.js';
import { DrawingManagerContext } from './DrawingManager.js';

/**
 * 그리기 툴박스를 생성합니다.
 *
 * 해당 컴포넌트는 반드시 `DrawingManager` 컴포넌트의 자식으로 존재해야 합니다.
 */
const Toolbox = /*#__PURE__*/React.forwardRef(function Toolbox(_ref, ref) {
  let {
    position: _position = kakao.maps.ControlPosition.TOP
  } = _ref;
  const position = typeof _position === "string" ? kakao.maps.ControlPosition[_position] : _position;
  const map = useMap("Toolbox");
  const drawingmanager = useContext(DrawingManagerContext);
  if (!drawingmanager) {
    throw new Error("Toolbox must exist inside DrawingManager Component!`");
  }
  const toolbox = useMemo(() => new kakao.maps.drawing.Toolbox({
    drawingManager: drawingmanager
  }), [drawingmanager]);
  useImperativeHandle(ref, () => toolbox, [toolbox]);
  useLayoutEffect(() => {
    const element = toolbox.getElement();
    map.addControl(element, position);
    return () => {
      map.removeControl(element);
    };
  }, [map, toolbox, position]);
  return null;
});

export { Toolbox };
