import Foundation
import SnowplowTracker

class SnowplowTrackerBuilder: NSObject, RequestCallback {
    let kNamespace = "WogaaTracker"
    
    func getTracker(_ url: String) -> TrackerController {
        var configurations : [Configuration] = []
        
        let networkConfig = NetworkConfiguration(endpoint: url, method: .post)
        
        configurations.append(networkConfig)
        
        let trackerConfiguration = TrackerConfiguration()
            .base64Encoding(true)
            .sessionContext(true)
            .platformContext(true)
            .lifecycleAutotracking(true)
            .screenViewAutotracking(true)
            .screenContext(true)
            .applicationContext(true)
            .exceptionAutotracking(true)
            .installAutotracking(true)
        
        configurations.append(trackerConfiguration)
        
        let emitterConfiguration = EmitterConfiguration()
        emitterConfiguration.requestCallback = self
        
        configurations.append(emitterConfiguration)
    
        let sessionConfiguration = SessionConfiguration()
        if #available(iOS 10, *) {
            sessionConfiguration.foregroundTimeout = Measurement(value: 10, unit: .minutes)
            sessionConfiguration.backgroundTimeout = Measurement(value: 5, unit: .minutes)
        } else {
            // Fallback on earlier versions
        }

        configurations.append(sessionConfiguration)
        
        let subjectConfiguration = SubjectConfiguration()
        
        configurations.append(subjectConfiguration)
        
        return Snowplow.createTracker(namespace: kNamespace, network: networkConfig, configurations: configurations)
    }
        
    func onSuccess(withCount successCount: Int) {
        print("Success: \(successCount)")
    }
    
    func onFailure(withCount failureCount: Int, successCount: Int) {
        print("Failure: \(failureCount), Success: \(successCount)")
    }  
}
