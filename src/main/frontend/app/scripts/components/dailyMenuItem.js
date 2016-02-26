import React from 'react'

var RenderList = React.createClass({
  render: function () {
    var listItems = 'N/A';
    if (this.props.data) {
      listItems = this.props.data.map(function (item) {
        return (
          <li key={item}>{item}</li>
        );
      });
    }
    if (listItems.length > 0) {
      return (
        <ul>{listItems}</ul>
      );
    } else {
      return <p>N/A</p>;
    }
  }
});


export default class DailyMenuItem extends React.Component {
  render() {
    return (
      <div className="menu-item">
        <h3>{this.props.restaurantName}</h3>
        <h4>Soups</h4>
        <RenderList data={this.props.soups}/>
        <h4>Main Dishes</h4>
        <RenderList data={this.props.mainDishes}/>
        <h4>Other</h4>
        <RenderList data={this.props.other}/>
      </div>
    );
  }
}
