'use strict';

var React = require('react');

const useIsomorphicLayoutEffect = typeof window !== "undefined" && typeof document !== "undefined" ? React.useLayoutEffect : React.useEffect;

exports.useIsomorphicLayoutEffect = useIsomorphicLayoutEffect;
