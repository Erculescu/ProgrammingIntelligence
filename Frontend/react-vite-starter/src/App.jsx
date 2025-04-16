import { Route, Routes } from "react-router"
import ChatPage from "./components/pages/ChatPage"
import LandingPage from "./components/pages/LandingPage"

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<LandingPage/>}/>
        <Route path="/chat/:model" element={<ChatPage/>}/>
      </Routes>
    </>
  )
}

export default App
