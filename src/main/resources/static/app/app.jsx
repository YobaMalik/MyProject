import React from "react";
import ReactDom from "react-dom";
import {BrowserRouter as Router, Route, Switch, Link  } from 'react-router-dom';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import './StyleSheet1.css';

class App extends React.Component {

render() {
return <p>Welcome 1to React!!</p>
    }
}
/*
const Router = ReactRouterDOM.BrowseRouter;
const Route = ReactRouterDOM.Route;
const Switch = ReactRouterDOM.Switch;
const Link = ReactRouterDOM.Link;

class App1 extends React.Component {
    render() {
        return (
            <Router>
            <Switch>

            <Link to="/uploadMultiFile">Upload Multiple Files</Link>
            <Link to="/stressCalculation">Расчет на прочность</Link>
            </Switch>
            </Router>
            );
    }
}
*/

var dataPipe = [
    { id: "Расчетное давление р =", value: '2' },
    { id: "Расчетная температура T =",  value: '5' },
    { id: "Допускаемое напряжение G =", value: '4' },
    { id: "Наружный диаметр =", value: '4' },
    { id: "Толщина", value: '4' }
];

function rowClassNameFormat(row, rowIdx) {
    // row is whole row object
    // rowIdx is index of row
    console.log(row)
    return row['name'] === 'George Michael' ?
        'GeorgeMichael-Row' : 'Other-Row';
}

class Table1 extends React.Component {
    render() {
        const cellEditProp = {
            mode: 'click', // 'dbclick' for trigger by double-click
            nonEditableColums: function () {
                return [1];
            }
        }

        return (


            <div>
                <BootstrapTable

                    data={this.props.data}

                    cellEdit={cellEditProp}
                    trClassName={rowClassNameFormat}>

                    <TableHeaderColumn isKey={ true} dataField='id'>
                        ID
          </TableHeaderColumn>
                    <TableHeaderColumn dataField='value' type=''>
                        Value
          </TableHeaderColumn>

                </BootstrapTable>

            </div>
        );
    }
}


class App1 extends React.Component {
    render() {
        return (
            <><div  className="App" >
                <p className="Table-header">Basic Table</p>
                <Table1 data={dataPipe} />
            </div>
            </>
        );
    }
}


class DropList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            options: ["Г1","Г2","Ж1","Ж2"]
        }
    }

    render() {
        return (
            <>
                <select id="aggrStateTPTC">{this.state.options.map((option, idx) =>
               <option key={idx}>{option}</option>)}
                </select>

                <br />
                Рабочее давление:
                <br />
                <input id="operPressTPTC" type="number" step="any" size="2"></input>
                 МПа
                <br />
                Диаметр:
                <br />
                <input id="DNTPTC" type="number" step="any" size="2"></input>
                мм
                <br />
                Расчетная температура
                <br />
                <input id="desTempTPTC"type="number" step="any" size="2"></input>
                 ºС
                </>
            )
    }
}

ReactDom.render(
    <App1 />,
    document.getElementById('root1')
);

ReactDom.render(
    <DropList />,
    document.getElementById('TPTC')
);

