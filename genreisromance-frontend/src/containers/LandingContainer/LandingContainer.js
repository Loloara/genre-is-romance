import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import Landing from 'components/Landing'

class LandingContainer extends Component {

  render() {
    return (
      <Fragment>
        {<Landing />}
      </Fragment>
    );
  }
}

export default connect(
  state => ({
  })
)(LandingContainer);