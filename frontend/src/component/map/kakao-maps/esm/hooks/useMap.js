import { useContext } from 'react';
import { KakaoMapContext } from '../components/Map.js';

/**
 * kakao map 객체를 가져오는 hook 입니다.
 * Map 객체 내부가 아니라면 Error를 발생 시킵니다.
 */
const useMap = componentName => {
  const kakaoMap = useContext(KakaoMapContext);
  if (!kakaoMap) {
    throw new Error(`${componentName ? componentName + " Component" : "useMap"} must exist inside Map Component!`);
  }
  return kakaoMap;
};

export { useMap };
