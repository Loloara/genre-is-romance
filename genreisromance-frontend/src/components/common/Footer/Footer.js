import React from 'react';
import classNames from 'classnames/bind';
import styles from './Footer.scss';

const cx = classNames.bind(styles);

const Footer = () => (
  <footer className={cx('footer')}>
    <div className={cx('content')}>© 2020 Copyright - Lee Jonggu</div>
  </footer>
);

export default Footer;