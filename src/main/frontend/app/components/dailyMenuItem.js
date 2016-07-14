import React from 'react';
import moment from 'moment';
import 'moment-timezone';

class RenderList extends React.Component {
  render() {
    var listItems = 'N/A';
    if (this.props.data) {
      listItems = this.props.data.map((item, i) => {
          return (
            <li key={i}>
              {item.foodTypes && item.foodTypes.map((type, j) => <span key={j} style={ JSON.parse(type.style) }
                                                                       className="category"
                                                                       title={type.type}>{type.type[0].toUpperCase()}</span>)}
              {item.description}
            </li>
          );
        }
      );
    }
    if (listItems.length > 0) {
      return (
        <ul>{listItems}</ul>
      );
    } else {
      return <p>N/A</p>;
    }
  }
}
RenderList.propTypes = {
  data: React.PropTypes.array
};

export default class DailyMenuItem extends React.Component {
  render() {
    if (this.props.rendered) {
      return (
        <div className="menu-item panel panel-default">
          <div className="panel-heading">
            <h4>{this.props.restaurantName}</h4>
            <small className="time-updated">
              @{(this.props.lastUpdated ? moment(this.props.lastUpdated, 'x').tz('Europe/Bratislava').format('HH:mm') : '--')}
            </small>
          </div>
          <div className="panel-body">
            <h5>Soups</h5>
            <RenderList data={this.props.soups}/>
            <h5>Main Dishes</h5>
            <RenderList data={this.props.mainDishes}/>
            {this.props.other.length > 0 && <h5>Other</h5> }
            {this.props.other.length > 0 && <RenderList data={this.props.other}/>}
          </div>
        </div>
      )
    } else {
      return null;
    }
  }
}
DailyMenuItem.propTypes = {
  rendered: React.PropTypes.bool,
  restaurantName: React.PropTypes.string,
  lastUpdated: React.PropTypes.number,
  mainDishes: React.PropTypes.array,
  soups: React.PropTypes.array,
  other: React.PropTypes.array
};
