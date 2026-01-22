import SwiftUI
import ExportSDK

struct ContentView: View {
    @State private var message = "Hello from iOS Demo!"
    @State private var showSDKFlow = false

    var body: some View {
        VStack(spacing: 20) {
            Text("Demo View")
                .font(.headline)

            Text(message)
                .padding()
                .background(Color.blue.opacity(0.1))
                .cornerRadius(8)

            Button("Test SDK") {
                // Navigate to full SDK flow
                message = "Opening SDK Framework... 🚀"
                NSLog("🚀 Opening ExportSDK flow")
                showSDKFlow = true
            }
            .padding()
            .background(Color.blue)
            .foregroundColor(.white)
            .cornerRadius(8)
            .fullScreenCover(isPresented: $showSDKFlow) {
                // Show full ExportSDK flow
                SDKFlowView(isPresented: $showSDKFlow)
            }

            Divider()

        }
        .padding()
    }
}

// Full-screen wrapper for ExportSDK flow
struct SDKFlowView: View {
    @Binding var isPresented: Bool

    var body: some View {
        ZStack {
            // ExportSDK Compose UI (full screen)
            ComposeFrameworkView()
                .ignoresSafeArea()

            // Close button overlay
            VStack {
                HStack {
                    Spacer()
                    Button(action: {
                        NSLog("🔙 Closing SDK flow")
                        isPresented = false
                    }) {
                        Image(systemName: "xmark.circle.fill")
                            .font(.system(size: 30))
                            .foregroundColor(.white)
                            .shadow(radius: 3)
                    }
                    .padding()
                }
                Spacer()
            }
        }
    }
}

struct ComposeFrameworkView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        // This calls the MainViewController from ExportSDK framework
        // Same API as used in iosApp
        return MainViewControllerKt.createMainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // No updates needed
    }
}



