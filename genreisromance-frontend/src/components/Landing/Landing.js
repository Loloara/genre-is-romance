import React, { Component } from 'react';
import classNames from 'classnames/bind';
import styles from './Landing.scss';

const cx = classNames.bind(styles);

class Landing extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            showFixBtn: false,
        };
    }

    componentDidMount() {
        window.addEventListener('scroll', this.listenToScroll);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.listenToScroll);
    }

    listenToScroll = () => {
        const yourPosition = this.instance.getBoundingClientRect().top;
        const isChanged = (yourPosition <= 100 ? true : false) ^ this.state.showFixBtn;

        if(isChanged){
            this.setState({
                showFixBtn: !this.state.showFixBtn
            });
        } 
    }

    render() {
        return(
            <div className={cx('Landing')}>
                <h2>영화관에서의 첫 만남에 설렘은 두 배가 되죠</h2>
                <div>
                    <p className={cx('sub')}>취향기반 영화데이트 매칭서비스</p>
                    <a href="http://naver.me/G8ZwdNkW" className={cx('go_btn')} target="_blank" rel="noopener noreferrer" title="새창">지금 예매하기</a>
                </div>

                <div className={cx('your')} ref={(el) => this.instance = el}>당신의 장르는 무엇인가요?</div>

                <div className={cx('recent')}>
				    <h3>최근에 어떤 영화 보셨어요?</h3>
				    <div className={cx('con')}>
                        어색한 분위기 속, 소개팅 상대에게
                        가장 많이 하는 질문 중 하나라고 해요.
                        영화의 감상을 나누고 취향을 공유하는 순간, 
                        ‘이 사람, 더 알고싶다!’ 설렘을 느끼게 되고
                        그렇게 자연스레 두번째, 세번째 만남이 성사되니까요.
                        
                        장르는로맨스는 당신에게도 
                        두번째와 세번째 만남이 찾아오길 응원합니다.
                        영화 취향을 담아 지원서를 꼼꼼히 작성해주세요. 
                        그러면 당신과 잘 어울리는 상대는 저희가 찾을게요.
                        두 분 모두 영화 데이트에 동의하시면, 
                        티켓도 대신 예매해드리겠습니다.

                        당신은 그저 영화관에서 설레는 첫 인사를 나누면 돼요.
                        첫 마디 후 밀려오는 어색함은 영화가 전부 책임져주겠죠?
                    </div>
                </div>
                
                <div className={cx('ticket')}>
                    <h3>장르는 로맨스 매표소</h3>
                    <strong>데이트 상대 제안 받기</strong>
                    <p>FREE</p>
                    <strong>데이트 비용</strong>
                    <p>
                        영화티켓 1만원 + 서비스이용료 2만원<br />
                        + 보증금 1만원 = <em>총 4만원</em><br />
                        <span>(티켓 값 따라 변동가능)</span>
                    </p>
                    <p className={cx('sm')}>
                        두 장의 티켓을 함께 찍어 보내주시면 보증금을 돌려드려요.<br />
                        (혹시 상대방이 나오지 않았다면, 2만원 다 당신의 것이에요)
                    </p>
                </div>

                <div className={cx('fix_btn')} style={{bottom: this.state.showFixBtn ? '0px' : '-150px'}}>
                    <a href="http://naver.me/G8ZwdNkW" target="_blank" rel="noopener noreferrer" title="새창">너와 나의 설레는 첫 만남, 지금 예매하기</a>
		        </div>
            </div>
        );
    }
}

export default Landing;