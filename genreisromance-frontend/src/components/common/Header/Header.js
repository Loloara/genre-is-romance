import React, { Component } from 'react';
import classNames from 'classnames/bind';
import styles from './Header.scss';

const cx = classNames.bind(styles);

class Header extends Component {
    render() {
        return(
            <div>
                I'm header
            </div>
        );
    }
}

export default Header;