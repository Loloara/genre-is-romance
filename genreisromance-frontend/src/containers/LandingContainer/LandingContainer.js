import React, { Component } from "react";
import { connect } from "react-redux";

class LandingContainer extends Component {

  render() {
    return (
      <div>
          I'm LandingContainer
      </div>
    );
  }
}

export default connect(
  state => ({
  })
)(LandingContainer);