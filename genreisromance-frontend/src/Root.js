import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import configure from 'store/configure';
import App from 'components/App';

const store = configure();

const Root = () => {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Route path="/" component={App} />
        <div className="App" />
      </BrowserRouter>
    </Provider>
  );
}

export default Root;
