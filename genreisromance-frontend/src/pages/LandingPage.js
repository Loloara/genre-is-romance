import React from 'react';
import PageTemplate from 'components/common/PageTemplate'
import LandingContainer from 'containers/LandingContainer'
import HeaderContainer from 'containers/HeaderContainer'

const LandingPage = () => {
  return (
    <PageTemplate header={<HeaderContainer/>}>
      <LandingContainer/>
    </PageTemplate>
  );
};

export default LandingPage;