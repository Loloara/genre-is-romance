import React, { Component } from 'react';
import { connect } from 'react-redux';
import Header from 'components/common/Header';

class HeaderContainer extends Component {

  componentDidUpdate(prevProps, prevState) {
    if (this.props !== prevProps) {
      this.setState({ showModal: this.props.showModal });
    }
  }

  render() {
    return (  
      <Header />
    );
  }
}

export default connect(
  state => ({
  })
)(HeaderContainer);