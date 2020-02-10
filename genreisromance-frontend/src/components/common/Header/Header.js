import React from 'react';
import classNames from 'classnames/bind';
import styles from './Header.scss';

const cx = classNames.bind(styles);

const Header = () => (
    <header className={cx('header')}>
        <h1 className={cx('content')}><a href="#;">영화같은 첫 만남, 장르는 로맨스</a></h1>
    </header>
);

export default Header;