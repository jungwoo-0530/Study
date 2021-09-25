import React, {Component} from "react";
import {FormControl} from "react-bootstrap";
import {Button, Form,  } from 'semantic-ui-react';
import axios from "axios";
import {} from "jquery.cookie";
import {NotificationContainer} from 'react-notifications';

axios.defaults.withCredentials = true;

class ServicePage extends Component {
    
    constructor(props){
        super(props);
        this.state = {
          isPaid: false,
        }
      }
    
    componentDidMount() {
        this.checkPermission()
    }
    
    //유저의 결제 권한 확인
    checkPermission = () =>{
        axios.get('/api/user/checkPermission').then((res)=>{
            if(res.data.message){
                alert(res.data.message)
                this.setState({
                    isPaid: false
                });
                this.props.history.push("/login");
            }
            else{
                this.setState({
                    isPaid: true
                });
            }
        })
    }

    search = () => {
        const keyword = this.keyword.value;
        if(this.state.isPaid===true) {
            axios.get('/api/search/authCheck').then((res)=>{
                if(res.data.message){
                    alert(res.data.message);
                    this.props.history.push("/");
                    return;
                }
                else{
                    axios.post('/api/search',{'keyword': keyword}).then((res) => {
                        alert(res.data.message)
                        this.props.history.push("/");
                        return;
                    }).catch((e) => {
                        alert(e)
                    })
                }
            })

        }
        else{

        }
    }

    handleKeyPress = (e) => {
        if (e.key === "Enter") {
            this.search()
        }
    };



        render() {
            const topStyle = {
                marginTop: "10%",
                marginLeft: "25%",
                marginRight: "25%",
                color: '#536976',
                fontSize: '18px'
            };
            const formStyle = {
                marginTop: "8%",
                width: "50%",
                marginLeft: "25%",
                marginRight: "25%",
                marginBottom: "20%",
            };
            const formInputStyle = {
                height: '40px'
            }
            const buttonStyle = {
                width: '15%',
                height: '40px',
                backgroundColor: '#536976',
                color: 'white'
            }

        return (
            <div>
                <NotificationContainer/>
                <div style={topStyle}>
                    <p>CIDS는 딥러닝을 이용한 저작권 침해 의심 탐지 사이트입니다.<br/><br/>간단한 키워드 입력으로 서비스를 이용할 수 있습니다.</p>
                </div>
                <Form style={formStyle}>
                    <Form.Group>
                        <FormControl style={formInputStyle}
                            ref={ref => (this.keyword = ref)}
                            onKeyPress={this.handleKeyPress}
                            placeholder="keyword"></FormControl>
                        <Button
                            onClick={this.search}
                            type="button"
                            style={buttonStyle}
                            active
                        >Go</Button>
                    </Form.Group>
                </Form>
            </div>
        )
    }
}


export default ServicePage;