import { useLayoutEffect, useEffect } from 'react';

const useIsomorphicLayoutEffect = typeof window !== "undefined" && typeof document !== "undefined" ? useLayoutEffect : useEffect;

export { useIsomorphicLayoutEffect };
