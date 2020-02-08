import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { LandingPage, NotFoundPage } from 'pages';

class App extends Component {
    
    render() {
        return (
            <div>
                <Switch>
                    <Route path="/" component={LandingPage} />
                    <Route component={NotFoundPage} />
                </Switch>       
            </div>
        );
    }
}

export default connect(
    state => ({
    })
)(App);