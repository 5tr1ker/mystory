import qs from "qs";

const OAuthMessageHandler = () => {
  const query = qs.parse(window.location.search, {
    ignoreQueryPrefix: true,
  });
  alert(query.message);
  window.location.replace("/login");

  return <></>;
};

export default OAuthMessageHandler;
