import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import Root from './Root';
import 'styles/base.scss';

ReactDOM.render(<Root />, document.getElementById('root'));
serviceWorker.unregister();
