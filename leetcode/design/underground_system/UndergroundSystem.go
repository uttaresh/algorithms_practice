// https://leetcode.com/problems/design-underground-system/submissions/

type TravelStat struct {
    sumOfTrips int
    numTrips int
    cachedAverage float64 // dumb hack to improve exec time... float arith seems to be costly
}

type OngoingTrip struct {
    id int
    startStation string
    startTime int
}

type UndergroundSystem struct {
    travelStats map[string]*TravelStat
    ongoingTrips map[int]OngoingTrip
}


func Constructor() UndergroundSystem {
    return UndergroundSystem {
        travelStats: make(map[string]*TravelStat),
        ongoingTrips: make(map[int]OngoingTrip),
    }
}


func (this *UndergroundSystem) CheckIn(id int, stationName string, t int)  {
    this.ongoingTrips[id] = OngoingTrip {
        id: id,
        startStation: stationName,
        startTime: t,
    }
    //this.PrintState()
}


func (this *UndergroundSystem) CheckOut(id int, stationName string, t int)  {
    trip := this.ongoingTrips[id]
    delete(this.ongoingTrips, id)
    key := trip.startStation + "->" + stationName
    var travelStat *TravelStat
    var statExists bool
    travelStat, statExists = this.travelStats[key]
    if !statExists {
        travelStat = &TravelStat {sumOfTrips:0, numTrips:0, cachedAverage:-1}
        this.travelStats[key] = travelStat
    }
    
    travelStat.sumOfTrips += t - trip.startTime
    travelStat.numTrips++
    travelStat.cachedAverage = -1
}


func (this *UndergroundSystem) GetAverageTime(startStation string, endStation string) float64 {
    key := startStation + "->" + endStation
    routeStats := this.travelStats[key]
    if routeStats.cachedAverage != -1 {
        return routeStats.cachedAverage
    }
    avg := float64(routeStats.sumOfTrips)/float64(routeStats.numTrips)
    routeStats.cachedAverage = avg
    return avg
}

func (this *UndergroundSystem) PrintState() {
    fmt.Println("Printing current stats:")
    for k, stat := range this.travelStats {
        fmt.Println(k, ":", strconv.Itoa(stat.sumOfTrips) + " ; " + strconv.Itoa(stat.numTrips))
    }
    
    fmt.Println("Printing ongoing trips:")
    for k, trip := range this.ongoingTrips {
        fmt.Println(strconv.Itoa(k), ":", trip.startStation, " @ ", strconv.Itoa(trip.startTime))
    }
}


/**
 * Your UndergroundSystem object will be instantiated and called as such:
 * obj := Constructor();
 * obj.CheckIn(id,stationName,t);
 * obj.CheckOut(id,stationName,t);
 * param_3 := obj.GetAverageTime(startStation,endStation);
 */
